import {axios} from '@/utils/request'

const api = {
    hotelPre: '/api/hotel',
    orderPre: '/api/order',
}

export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}

/* 迭代3.3删除addHotelAPI，移动到admin里面
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
 */


//迭代3.1新增
/* 迭代3.3搬运到admin
export function deleteHotelAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteHotel`,
        method: `POST`,
    })
}

 */

//迭代3.2新增
export function executeOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/executeOrder`,
        method: `POST`,
    })
}

export function finishOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/finishOrder`,
        method: `POST`,
    })
}

//迭代3.3新增
export function retrieveHotelByManagerAPI(managerId) {
    return axios({
        url: `${api.hotelPre}/${managerId}/retrieveHotelByManager`,
        method: `GET`,
    })
}

export function allOrdersAPI(hotelId) {
    return axios({
        url: `${api.orderPre}/${hotelId}/allOrders`,
        method: `GET`,
    })
}

export function updateHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/updateHotelInfo`,
        method: 'POST',
        data
    })
}

export function deleteCooperativeCompanyAPI(data) {
    return axios({
        url: `${api.hotelPre}/deleteCooperativeCompany`,
        method: `POST`,
        data
    })
}

export function addCooperativeCompanyAPI(data) {
    return axios({
        url: `${api.hotelPre}/addCooperativeCompany`,
        method: `POST`,
        data
    })
}

//3.7
export function updateRoomCurNumAPI(hotelId, roomType, num) {
    return axios({
        url: `${api.hotelPre}/updateRoomCurNum`,
        method: 'POST',
        params: {hotelId, roomType, num},
    })
}

export function deleteServiceAPI(hotelId, type) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteService`,
        method:'POST',
        params:{hotelId,type},
    })
}

export function addServiceAPI(hotelId, type) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/addService`,
        method:'POST',
        params:{hotelId,type},
    })
}

export function getAllServicesAPI() {
    return axios({
        url: `${api.hotelPre}/getAllServices`,
        method:'GET',
    })
}
