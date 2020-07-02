import {axios} from '@/utils/request'

const api = {
    userPre: '/api/user',
    commentPre: '/api/comment',
    memberPre: '/api/member',
}

export function loginAPI(data) {
    return axios({
        url: `${api.userPre}/login`,
        method: 'POST',
        data
    })
}

export function registerAPI(data) {
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}

export function getUserInfoAPI(id) {
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}

export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}

//3.4
export function postCommentAPI(data) {
    return axios({
        url: `${api.commentPre}/postComment`,
        method: 'POST',
        data,
    })
}

//3.4.5
export function getMemberInfoAPI(userId) {
    return axios({
        url: `${api.memberPre}/${userId}/getMemberInfo`,
        method: 'GET',
    })
}

export function registerMemberAPI(data) {
    return axios({
        url: `${api.memberPre}/registerMember`,
        method: 'POST',
        data
    })
}

//3.6.1
export function getUserCreditRecordsAPI(userId) {
    return axios({
        url: `${api.userPre}/${userId}/getUserCreditRecords`,
        method: `GET`,
    })
}

