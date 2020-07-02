import {axios} from '@/utils/request'

const api = {
    orderPre: '/api/order'
}

export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}

export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}

export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}

export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}

export function deleteOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/deleteOrder`,
        method: 'POST',
    })
}

//迭代3.2新增，通过单个orderId获取订单信息
export function retrieveOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/retrieveOrder`,
        method: 'GET',
    })
}

