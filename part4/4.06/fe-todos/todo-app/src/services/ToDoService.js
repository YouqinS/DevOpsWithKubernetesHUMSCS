  
import axios from 'axios'
const baseUrl = window.location.protocol + '//'+window.location.hostname+':9999/'
const todoUrl = baseUrl + "todo"
const slash = "/"

const getToDos = async () => {
    const request = axios.get(todoUrl)
    const response = await request
    return response.data
}

const getImage = async () => {
    const request = axios.get(baseUrl + "image", {
        responseType: 'arraybuffer'})
    const response = await request
    return response.data
}


const createToDo = async newObject => {
    console.log("service: createToDo");
    const axiosConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*"
        }
    };
    const request = axios.post(todoUrl, newObject, axiosConfig)
    const response = await request
    return response.data
}

const setToDone = async (id) => {
    console.log("setToDone: id=" + id);
    const todoIdUrl = todoUrl + slash + id
    const axiosConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*"
        }
    };

    const request = axios.put(todoIdUrl, axiosConfig)

    const response = await request
    return response.data
}

export default {
    getToDos,
    createToDo,
    getImage,
    setToDone
}