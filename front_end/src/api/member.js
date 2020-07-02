import {axios} from '@/utils/request'
import {executeOrderAPI} from "./hotelManager";

const api = {
    userPre: '/api/user',
    memberPre: '/api/member',
}


//3.7
export function getLevelInfoAPI(){
    return axios({
        url: `${api.memberPre}/getLevelInfo`,
        method: 'GET',
    })
}

export function changeLevelInfoAPI(data) {
    return axios({
        url: `${api.memberPre}/changeLevelInfo`,
        method: 'POST',
        data,
    })
}