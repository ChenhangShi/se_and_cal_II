import {axios} from '@/utils/request'

const api = {
    orderPre: '/api/order',
    userPre: '/api/user',
}

//3.5 获取所有用户，信用充值的时候用
export function getAllUsersAPI() {
    return axios({
        url: `${api.userPre}/getAllUsers`,
        method: 'GET',
    })
}
// 信用充值
export function addCreditAPI(userId, amount) {
    return axios({
        url: `${api.userPre}/${userId}/addCredit`,
        method: 'POST',
        params: {userId, amount}
    })
}
// 获取异常订单
export function getUnusualOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getUnusualOrders`,
        method: 'GET',
    })
}
// 撤销异常订单，其中传入信用值的恢复方式
export function annualUnusualOrderAPI(orderId, restoreMethod) {
    return axios({
        url: `${api.orderPre}/${orderId}/annualUnusualOrder`,
        method: 'POST',
        params: {orderId, restoreMethod}
    })
}