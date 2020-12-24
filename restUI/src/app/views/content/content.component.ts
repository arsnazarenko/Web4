import {Component, OnInit, ViewChild} from '@angular/core';
import {Point} from '../../model/point';
import {PointService} from '../../service/point.service';
import {PointGraphComponent} from '../point-graph/point-graph.component';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  @ViewChild(PointGraphComponent) private graph: PointGraphComponent;

  private pointsData: Point[];

  constructor(private pointService: PointService) {
  }

  onPointsUpdate(radiusInfo: any): void {
    this.graph.changePoints(radiusInfo);
  }

  ngOnInit(): void {
    this.pointService.getPoints()
      .subscribe((data: Point[]) => {
        console.log(data);
        this.points = data;
      });
  }

  private increaseSort(a, b): number {
    if (a.id > b.id) {
      return 1;
    } else if (a.id < b.id) {
      return -1;
    } else {
      return 0;
    }
  }

  get points(): Point[] {
    return this.pointsData;
  }

  set points(points: Point[]) {
    this.pointsData = points.sort(this.increaseSort);
  }

}
