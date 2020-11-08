import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/sys/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/sys/v1/users/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/sys/logout',
    method: 'post'
  })
}

export function getUsers(params){
  return request({
    url: '/sys/v1/users',
    method: 'get',
    params
  })
}

export function createUser(data){
  return request({
    url: '/sys/v1/users',
    method: 'post',
    data
  })
}

export function updateUser(data){
  return request({
    url: '/sys/v1/users/'+ data.userId,
    method: 'put',
    data
  })
}

export function deleteUser(userId){
  return request({
    url: '/sys/v1/users/'+ userId,
    method: 'delete',
  })
}