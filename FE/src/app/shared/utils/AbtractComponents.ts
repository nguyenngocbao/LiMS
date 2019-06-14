import { ToastrManager } from 'ng6-toastr-notifications';

export class AbtractComponents{
    
    constructor(public toastrM: ToastrManager){

    }

    public notifySucccess(mess){
        this.toastrM.successToastr(mess, '', {
            toastTimeout: 2000,
            position: 'top-center'
          })
    } 
    public notifyưarning(mess){
        this.toastrM.warningToastr(mess, '', {
            toastTimeout: 2000,
            position: 'top-center'
          })
    } 
    public notifyError(mess){
        this.toastrM.errorToastr(mess, '', {
            toastTimeout: 2000,
            position: 'top-center'
          })
    } 
    public notifyInfo(mess){
        this.toastrM.infoToastr(mess, '', {
            toastTimeout: 2000,
            position: 'top-center'
          })
    } 
}