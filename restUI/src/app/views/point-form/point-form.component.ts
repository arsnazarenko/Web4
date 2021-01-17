import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {PointService} from '../../service/point.service';
import {Point} from '../../model/point';
import {PointRequest} from '../../model/pointRequest';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-point-form',
  templateUrl: './point-form.component.html',
  styleUrls: ['./point-form.component.css']
})

export class PointFormComponent implements OnInit, OnDestroy {
  myForm: FormGroup;
  @Input() points: Point[];
  @Output() pointsUpdated = new EventEmitter<any>();

  private changeSubscription: Subscription;

  constructor(private pointService: PointService) {
    this.myForm = new FormGroup({
      xValue: new FormControl(null, [Validators.required, Validators.pattern(/^[-]?(((0{1}|[1-4]){1}(\.[0-9]+)?)|5(\.0+)?)$/)]),
      yValue: new FormControl(1, Validators.required),
      rValue: new FormControl(null, [Validators.required, Validators.pattern(/^(([1-4]{1}(\.[0-9]+)?)|5(\.0+)?)$/)])
    });
  }

  ngOnInit(): void {
    this.changeSubscription = this.myForm.controls.rValue.valueChanges.subscribe(r => {
      this.onPointsUpdate({radius: this.myForm.controls.rValue.value, valid: this.myForm.controls.rValue.valid});
    });
  }

  onPointsUpdate(radiusInfo: any): void {
    this.pointsUpdated.emit(radiusInfo);
  }


  onSubmit(): void {
    const xVal = this.myForm.controls.xValue.value;
    const yVal = this.myForm.controls.yValue.value;
    const rVal = this.myForm.controls.rValue.value;
    this.pointService
      .addPoint({x: xVal, y: yVal, r: rVal} as PointRequest)
      .subscribe(data => {
        console.log(data);
        this.points.push(data);
        // будем передавать объект {валидноть, радиус}, валидность будем получать из состояний формы
        this.onPointsUpdate({radius: rVal, valid: this.myForm.controls.rValue.valid});
      });
  }

  ngOnDestroy(): void {
    if (this.changeSubscription) {
      this.changeSubscription.unsubscribe();
    }
  }
}
