export class NodeValidatator {
    static isValidNode(node: any) {
        if ('P' === node.tagName || this.isHeadingNode(node)) {
            // console.log('isTrackingNode --Node is not P or H', node)
            return true
        }
        return false
    }
    static isHeadingNode(node: any) {
        // tslint:disable-next-line: max-line-length
        if ('H1' === node.tagName || 'H2' === node.tagName || 'H3' === node.tagName || 'H4' === node.tagName || 'H5' === node.tagName || 'H6' === node.tagName) {
            return true
        }
        return false
    }
    static isMasterNode(node: any) {
        if ('BODY' === node.tagName || 'HEAD' === node.tagName) {
            return true
        }
        return false
    }
}

