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

//迭代3.2新增 把订单状态设为已执行
export function executeOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/executeOrder`,
        method: `POST`,
    })
}
// 把订单状态设为已退房
export function finishOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/finishOrder`,
        method: `POST`,
    })
}

//迭代3.3新增 获取该酒店管理人员所绑定的酒店
export function retrieveHotelByManagerAPI(managerId) {
    return axios({
        url: `${api.hotelPre}/${managerId}/retrieveHotelByManager`,
        method: `GET`,
    })
}
// 获取该酒店的全部订单
export function allOrdersAPI(hotelId) {
    return axios({
        url: `${api.orderPre}/${hotelId}/allOrders`,
        method: `GET`,
    })
}
// 酒店工作人员更新酒店信息
export function updateHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/updateHotelInfo`,
        method: 'POST',
        data
    })
}
// 3.7 删除合作企业
export function deleteCooperativeCompanyAPI(data) {
    return axios({
        url: `${api.hotelPre}/deleteCooperativeCompany`,
        method: `POST`,
        data
    })
}
// 添加合作企业
export function addCooperativeCompanyAPI(data) {
    return axios({
        url: `${api.hotelPre}/addCooperativeCompany`,
        method: `POST`,
        data
    })
}

//3.7 用于有线下预订时，更新房间数量
export function updateRoomCurNumAPI(hotelId, roomType, num) {
    return axios({
        url: `${api.hotelPre}/updateRoomCurNum`,
        method: 'POST',
        params: {hotelId, roomType, num},
    })
}
// 删除酒店服务
export function deleteServiceAPI(hotelId, type) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteService`,
        method:'POST',
        params:{hotelId,type},
    })
}
// 添加酒店服务
export function addServiceAPI(hotelId, type) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/addService`,
        method:'POST',
        params:{hotelId,type},
    })
}
// 获取所有酒店服务
export function getAllServicesAPI() {
    return axios({
        url: `${api.hotelPre}/getAllServices`,
        method:'GET',
    })
}
