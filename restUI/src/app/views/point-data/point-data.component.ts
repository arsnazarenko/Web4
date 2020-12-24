import {Component, Input} from '@angular/core';
import {Point} from '../../model/point';
@Component({
  selector: 'app-point-data',
  templateUrl: './point-data.component.html',
  styleUrls: ['./point-data.component.css']
})
export class PointDataComponent{
  @Input() points: Point[];

  constructor() {}




}
