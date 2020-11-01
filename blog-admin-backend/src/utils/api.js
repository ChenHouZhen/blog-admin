import axios from 'axios'
import {Message} from 'element-ui'


// 请求拦截
// 在这里可以加一个 loading 操作
axios.interceptors.request.use(config =>{
    console.log("拦截请求...")
    return config;
},err => {
    Message.error({message:"请求超时！"})
})


// 响应拦截
// 可以在这里做错误信息统一处理，比如拦截登陆跳转、拦截错误信息等操作
axios.interceptors.response.use(data => {
    console.log("拦截响应...")
    return data;
})

let base = '/api'
export const postRequest = (url, params) => {
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:params,
        // 为什么要下面这个？
        transformRequest:[function(data){
            let ret = ''
            for(let it in data){
                ret +=encodeURIComponent(it) + '='+ encodeURIComponent(data[it]) + '&'
            }
            return ret;
        }],
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
}

export const getRequest = (url) =>{
    return axios({
        method:'get',
        url:`${base}${url}`
    })
}

