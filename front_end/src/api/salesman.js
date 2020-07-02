import {axios} from '@/utils/request'

const api = {
    orderPre: '/api/order',
    userPre: '/api/user',
}

//3.5
export function getAllUsersAPI() {
    return axios({
        url: `${api.userPre}/getAllUsers`,
        method: 'GET',
    })
}

export function addCreditAPI(userId, amount) {
    return axios({
        url: `${api.userPre}/${userId}/addCredit`,
        method: 'POST',
        params: {userId, amount}
    })
}

export function getUnusualOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getUnusualOrders`,
        method: 'GET',
    })
}

export function annualUnusualOrderAPI(orderId, restoreMethod) {
    return axios({
        url: `${api.orderPre}/${orderId}/annualUnusualOrder`,
        method: 'POST',
        params: {orderId, restoreMethod}
    })
}