import {axios} from '@/utils/request'
import {executeOrderAPI} from "./hotelManager";

const api = {
    userPre: '/api/user',
    memberPre: '/api/member',
}


//3.7 获取等级表，所有用户均可查看
export function getLevelInfoAPI(){
    return axios({
        url: `${api.memberPre}/getLevelInfo`,
        method: 'GET',
    })
}
// 修改登记记录，只有网站营销人员调用
export function changeLevelInfoAPI(data) {
    return axios({
        url: `${api.memberPre}/changeLevelInfo`,
        method: 'POST',
        data,
    })
}