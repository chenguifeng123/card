import axios from 'axios';

//let base = 'http://localhost:9090';
let base = '';
var qs = require('qs');
var instance = axios.create({ headers: { 'content-type': 'application/json;charset=utf-8' } });

export const requestLogin = params => { return instance.post(`${base}/login`, params).then(res => res.data); };

export const listMemberCards = params => {
  let { pageNo, pageSize } = params;
  return instance.get(`${base}/listMemberCards/${pageNo}-${pageSize}`, JSON.stringify(params));
}

export const addMemberCard = params => { return instance.put(`${base}/memberCard`, params); };

export const updateMemberCard = params => { return instance.post(`${base}/memberCard`, params); };

export const deleteMemberCard = params => {
  let { id } = params;
  return instance.delete(`${base}/memberCard/${id}`, params);
};
