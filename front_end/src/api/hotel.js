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

//3.4
export function getHotelCommentsAPI(hotelId) {
    return axios({
        url: `${api.commentPre}/${hotelId}/getHotelComments`,
        method: 'GET',
    })
}

export function getAllBizRegionsAPI() {
    return axios({
        url: `${api.hotelPre}/getAllBizRegions`,
        method: 'GET',
    })
}

//3.6
export function getAllRoomsAPI() {
    return axios({
        url: `${api.hotelPre}/getAllRooms`,
        method: 'GET',
    })
}

