export class HighLightor {
    private _elements: Array<any>
    constructor(elements: Array<any>) {
        this._elements = elements
    }
    get elements() {
        return this._elements
    }
    set elements(elements: Array<any>) {
        this._elements = elements
    }
    highLight() {
        this._elements.forEach((element) => {
            if (element.tagName === 'P') {
                element.style.backgroundColor = '#FE8A8E'
              } else {
                element.style.border = '2px solid red'
              }
        })
    }
    highLightAndBindAction(dom: any, actionName: string, functionToBind: Function, objectRange: any) {
        this._elements.forEach((element) => {
            dom.bind(element, actionName, functionToBind, objectRange)
            element.setAttribute('title', 'Double Click Go To Link: Protocol_Section-4_P-25')
        })
    }
}
