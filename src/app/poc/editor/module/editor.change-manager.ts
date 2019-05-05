import { NodeValidatator } from '../util/node-validator'

export class ChangeManager {
    private _changeNodes: Array<any>
    private _trackNodes: Array<any>
    private _originalNodesData: Array<any>
    private _count: number
    constructor(trackNodes: Array<any>) {
        this._changeNodes = []
        this._count = 0
        this._trackNodes = trackNodes
        this._originalNodesData = []
        this.storingOriginalData(this._trackNodes)
    }
    set trackNodes(trackNodes: Array<any>) {
        this._trackNodes = trackNodes
        this.storingOriginalData(this._trackNodes)
    }
    get changeNodes() {
        return this._changeNodes
    }
    private pushToChangeNodes(el: any) {
        const temp = this._changeNodes.find(function (obj) {
            return obj.id === el.id
        })
        if (temp) {
            temp.element = el
        } else {
            this._changeNodes.push({ id: el.id, element: el })
        }
    }
    private isTrackingNode(node: any) {
        if (NodeValidatator.isMasterNode) {
            return false
        }
        if (!NodeValidatator.isValidNode) {
            return false
        }
        if (!node.id) {
            return false
        }
        let result = false
        this._trackNodes.forEach(element => {
            if (element.isSameNode(node)) {
                result = true
                return
            }
        })
        return result
    }
    private storingOriginalData(nodes: Array<any>) {
        nodes.forEach(node => {
            this._originalNodesData.push(
                {
                    id: node.id,
                    text: node.innerText,
                    html: node.outerHTML,
                }
            )
        })
    }
    watchNodeChange(parents: Array<any>) {
        parents.forEach(parent => {
            if (this.isTrackingNode(parent)) {
                this.pushToChangeNodes(parent)
                return
            } else {
                if (NodeValidatator.isValidNode(parent)) {
                    if (!parent.id) {
                        parent.id = 'new-' + this._count++
                    }
                    this.pushToChangeNodes(parent)
                    return
                }
            }
        })
    }
    getNewElements() {
        return this._changeNodes.filter(this.filterNewNode)
    }
    getDeletedElements() {
        return this._changeNodes.filter(this.filterDeleteNode)
    }
    getModifiedElements() {
        return this._changeNodes.filter(node => {
            if (!node.id.match(/new-/g)) {
                const originalNode = this._originalNodesData.find(el => node.id === el.id)
                if ((node.element.innerText !== '') && (node.element.outerHTML !== originalNode.html)) {
                    return true
                }
            }
        })
    }
    private filterNewNode(node) {
        if (node.id.match(/new-/g)) {
            if (node.element.innerText !== '') {
                return true
            }
        }
        return false
    }
    private filterDeleteNode(node) {
        if (!node.id.match(/new-/g)) {
            if (node.element.innerText === '') {
                return true
            }
        }
        return false
    }
    getResult() {
        const modifiedNodes = []
        this.getModifiedElements().forEach((node) => {
            const originalNode = this._originalNodesData.find(el => el.id === node.id)
            modifiedNodes.push(
                {
                    nodeId: node.id,
                    previousNodeId: node.element.previousSibling.id,
                    contentOriginal: originalNode.html,
                    contentNew: node.element.outerHTML,
                }
            )
        })
        const newNodes = []
        this.getNewElements().forEach((node) => {
            newNodes.push(
                {
                    nodeId: node.id,
                    previousNodeId: node.element.previousSibling.id,
                    contentOriginal: '',
                    contentNew: node.element.outerHTML,
                }
            )
        })
        const deletedNodes = []
        this.getDeletedElements().forEach((node) => {
            deletedNodes.push(
                {
                    nodeId: node.id,
                    previousNodeId: '',
                    contentOriginal: '',
                    contentNew: node.element.outerHTML,
                }
            )
        })
        return {
            modifiedNodes: modifiedNodes,
            newNodes: newNodes,
            deletedNodes: deletedNodes
        }
    }
}
