import { FlatTreeControl } from '@angular/cdk/tree'
import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core'
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree'
import {  Observable, of as observableOf, BehaviorSubject } from 'rxjs'
import { Tree } from './shared/Models/Tree'
import { TreeFlatNode } from './shared/Models/TreeFlat'

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent implements OnInit {

  dataChange = new BehaviorSubject<Tree[]>([])
  @Output()
  getView: EventEmitter<number> = new EventEmitter<number>()
  @Input()
  set dataTree(dataTree: any) {
    this.setTree(dataTree)
  }
  isActive = ''
  treeControl: FlatTreeControl<TreeFlatNode>
  treeFlattener: MatTreeFlattener<Tree, TreeFlatNode>
  dataSource: MatTreeFlatDataSource<Tree, TreeFlatNode>

  constructor() {
    this.treeFlattener = new MatTreeFlattener(this.transformer, this._getLevel,
    this._isExpandable, this._getChildren)
    this.treeControl = new FlatTreeControl<TreeFlatNode>(this._getLevel, this._isExpandable)
    this.dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener)
    this.dataChange.subscribe(data => this.dataSource.data = data)
  }

  ngOnInit() {
  }
  get data(): Tree[] { return this.dataChange.value }

  setTree(data) {
    this.dataChange.next(data)
}
  transformer = (node: Tree, level: number) => {
    return new TreeFlatNode( node.Children.length > 0, node.Text, level, node.Index)
  }

  private _getLevel = (node: TreeFlatNode) => node.level

  private _isExpandable = (node: TreeFlatNode) => node.expandable

  private _getChildren = (node: Tree): Observable<Tree[]> => observableOf(node.Children)

  hasChild = (_: number, _nodeData: TreeFlatNode) => _nodeData.expandable


  viewContent(id) {
    this.getView.emit(id)
    this.isActive = id
  }
}
