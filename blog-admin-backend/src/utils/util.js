
import {getRequest} from './api'

export const initMenu = (router, store) => {
    if(store.state.routes.length > 0){
        return;
    }

    getRequest('/config/sysmenu').then(resp => {
        console.log("resp",resp);
        if(resp && resp.status == 200){
        }
    })
}