import {axios} from '@/utils/request'

const api = {
    hotelPre: '/api/hotel',
    commentPre: '/api/comment',
}

export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}

export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}

//3.4 获取酒店评论
export function getHotelCommentsAPI(hotelId) {
    return axios({
        url: `${api.commentPre}/${hotelId}/getHotelComments`,
        method: 'GET',
    })
}
// 获取所有商圈，用于添加酒店的时候商圈下拉框的内容
export function getAllBizRegionsAPI() {
    return axios({
        url: `${api.hotelPre}/getAllBizRegions`,
        method: 'GET',
    })
}

//3.6 获取所有房间信息，用于主界面筛选信息
export function getAllRoomsAPI() {
    return axios({
        url: `${api.hotelPre}/getAllRooms`,
        method: 'GET',
    })
}

