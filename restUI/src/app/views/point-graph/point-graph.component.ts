import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {PointService} from '../../service/point.service';
import {Point} from '../../model/point';
import {PointRequest} from '../../model/pointRequest';

@Component({
  selector: 'app-point-graph',
  templateUrl: './point-graph.component.html',
  styleUrls: ['./point-graph.component.css']
})
export class PointGraphComponent implements OnInit {
  @Input() points: Point[];

  @ViewChild ('canvasGraph', {static: true} )
  private canvasGraph: ElementRef <HTMLCanvasElement>;

  @ViewChild ('canvasPoints', {static: true} )
  private canvasPoints: ElementRef <HTMLCanvasElement>;

  private pointsCtx: CanvasRenderingContext2D;
  private graphCtx: CanvasRenderingContext2D;

  private usedRadius: number;
  private radiusIsValid: boolean;

  private width: number;
  private height: number;
  private rHalfLen: number;

  constructor(private pointService: PointService) {
    // При старте отрисовка идет со значением R = 1
    this.usedRadius = 1;
    this.radiusIsValid = false;
  }


   addPoint(event: MouseEvent): void {
    if (this.radiusIsValid === true) {
      const xVal: number = event.clientX - this.canvasPoints.nativeElement.getBoundingClientRect().left;
      const yVal: number = event.clientY - this.canvasPoints.nativeElement.getBoundingClientRect().top;
      const pointReq: PointRequest = this.toRadiusCoordinate({x: xVal, y: yVal});
      this.pointService
        .addPoint(pointReq)
        .subscribe(data => {
          console.log(data);
          this.points.push(data);
          this.redrawPoints();
        });
    }
  }

  ngOnInit(): void {
    this.graphCtx = this.canvasGraph.nativeElement.getContext('2d');
    this.pointsCtx = this.canvasPoints.nativeElement.getContext('2d');
    this.height = this.canvasGraph.nativeElement.height;
    this.width = this.canvasGraph.nativeElement.width;
    this.rHalfLen = this.width / 6;
    this.drawAreas();
    this.drawAxles();
    this.redrawPoints();
    // fixme: Отрисовка заднего слоя графика
  }


  public changePoints(radiusInfo: any): void {
    if (radiusInfo.valid === true) {
      this.radiusIsValid = true;
      this.usedRadius = radiusInfo.radius;
      console.log(`repaint method worked with radius : ${radiusInfo.radius} and is valid: ${radiusInfo.valid}`);
      this.redrawPoints();
    } else {
      this.radiusIsValid = false;
      console.log('not valid');
    }
  }

  private toRadiusCoordinate(pointCoordinate: any): PointRequest {
    const xCoord = Math.round(((pointCoordinate.x - this.width / 2) / (this.width / 6)) * (this.usedRadius / 2) * 1000) / 1000;
    const yCoord = Math.round(((-(pointCoordinate.y - this.height / 2) / (this.height / 6)) * (this.usedRadius / 2)) * 1000) / 1000;
    return {
      x: (xCoord === 0) ? 0 : xCoord,
      y: (yCoord === 0) ? 0 : yCoord,
      r: this.usedRadius
    } as PointRequest;
  }



  private toElementCoordinate(point: Point): any {
    return {
      x: (point.x * (this.width / 6) / (this.usedRadius / 2) + (this.width / 2)),
      y: this.height - (point.y * (this.height / 6) / (this.usedRadius / 2) + (this.height / 2))
    };
  }


  private drawPoint(point: Point): void {
    this.pointsCtx.beginPath();
    this.pointsCtx.strokeStyle = 'black';
    const pointCoordinate = this.toElementCoordinate(point);
    this.pointsCtx.arc(pointCoordinate.x, pointCoordinate.y, 3, 0, Math.PI * 2, true);
    if (Number(this.usedRadius) === Number(point.r)) {
      this.pointsCtx.fillStyle = point.result ? '#1dc41e' : '#FF4E14';
      this.pointsCtx.stroke();
    } else {
      this.pointsCtx.fillStyle = 'rgba(133,133,133,0.5)';
    }
    this.pointsCtx.fill();
    this.pointsCtx.closePath();
  }


  private redrawPoints(): void {
    this.pointsCtx.clearRect(0, 0, this.width, this.height);
    this.points.forEach(point => this.drawPoint(point));
  }

  private drawAreas(): void {
    this.graphCtx.beginPath();
    this.graphCtx.strokeStyle = 'black';
    this.graphCtx.fillStyle = '#3FDAFF';
    this.graphCtx.rect(this.width / 2 - this.rHalfLen * 2, this.height / 2, this.rHalfLen * 2, this.rHalfLen);
    this.graphCtx.fill();
    this.graphCtx.closePath();
    this.graphCtx.beginPath();
    const circle = new Path2D();
    circle.moveTo(this.width / 2, this.height / 2);
    circle.arc(this.width / 2, this.height / 2, this.rHalfLen, 0, Math.PI / 2, false);
    this.graphCtx.fill(circle);
    this.graphCtx.closePath();
    this.graphCtx.beginPath();
    this.graphCtx.moveTo(this.width / 2, this.height / 2);
    this.graphCtx.lineTo(this.width / 2, this.height / 2 - 2 * this.rHalfLen);
    this.graphCtx.lineTo(this.width / 2 - this.rHalfLen, this.height / 2);
    this.graphCtx.fill();
    this.graphCtx.closePath();

  }

  private drawAxles(): void {
    this.graphCtx.fillStyle = 'black';
    this.graphCtx.beginPath();
    this.graphCtx.moveTo(0, this.height / 2);
    this.graphCtx.lineTo(this.width, this.height / 2);
    this.graphCtx.moveTo(this.width / 2, 0);
    this.graphCtx.lineTo(this.width / 2, this.height);
    this.graphCtx.stroke();
    let posX = this.width / 2 - this.rHalfLen * 2;
    let posY = this.height / 2 + this.rHalfLen * 2;
    for (let j = 0; j < 2; j++) {
      for (let i = 0; i < 2; i++) {
        this.graphCtx.moveTo(posX + i * this.rHalfLen, this.height / 2 - 3);
        this.graphCtx.lineTo(posX + i * this.rHalfLen, this.height / 2 + 3);
        this.graphCtx.stroke();
        this.graphCtx.moveTo(this.width / 2 - 3, posY - i * this.rHalfLen);
        this.graphCtx.lineTo(this.width / 2 + 3, posY - i * this.rHalfLen);
        this.graphCtx.stroke();
      }
      posX += this.rHalfLen * 3;
      posY -= this.rHalfLen * 3;
    }
    this.graphCtx.font = '16px sans-serif';
    this.graphCtx.textAlign = 'left';
    this.graphCtx.textBaseline = 'middle';
    this.graphCtx.fillText('-R/2', this.width / 2 + 5, this.height / 2 + this.rHalfLen);
    this.graphCtx.fillText('R/2', this.width / 2 + 5, this.height / 2 - this.rHalfLen);
    this.graphCtx.fillText('-R', this.width / 2 + 5, this.height / 2 + this.rHalfLen * 2);
    this.graphCtx.fillText('R', this.width / 2 + 5, this.height / 2 - this.rHalfLen * 2);
    this.graphCtx.textAlign = 'center';
    this.graphCtx.textBaseline = 'bottom';
    this.graphCtx.fillText('R/2', this.width / 2 + this.rHalfLen, this.height / 2);
    this.graphCtx.fillText('-R/2', this.width / 2 - this.rHalfLen, this.height / 2);
    this.graphCtx.fillText('R', this.width / 2 + this.rHalfLen * 2, this.height / 2);
    this.graphCtx.fillText('-R', this.width / 2 - this.rHalfLen * 2, this.height / 2);
    this.graphCtx.closePath();
  }



}
