const VALID_ELEMENTS = '*[id|class|title|align|role|colspan|style|data-options|highlight|src|height|width|href|itemid|dir|linkProtocol]'
const baseConfig: object = {
    height: '730px',
    branding: false,
// tslint:disable-next-line: max-line-length
    plugins: 'imagetools print preview fullpage searchreplace autolink directionality visualblocks visualchars fullscreen image code link media template table charmap hr pagebreak nonbreaking toc insertdatetime advlist lists wordcount imagetools textpattern help',
// tslint:disable-next-line: max-line-length
    toolbar: 'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat save',
    entity_encoding: 'raw',
    visual : false,
    imageUrl: 'http://192.168.191.225:9434/api/docx/images',
    content_css: 'assets/tiny.css' 
}
function getSapConfig() {
    const prop = { valid_elements: VALID_ELEMENTS }
    return  Object.assign(prop, baseConfig)
}
function getProtocolConfig() {
    const prop = { valid_elements: VALID_ELEMENTS}
    return  Object.assign(prop, baseConfig)
}
export let protocolConfig: object = getSapConfig()
export let sapConfig: object = getProtocolConfig()
export let specConfig: object = baseConfig
export let commonConfig: object = baseConfig
