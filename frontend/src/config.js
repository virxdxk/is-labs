const MODE = 'prod'

const PORT = import.meta.env.API_PORT || 8080

export let API_BASE_PATH;
export let WS_BASE_PATH;

if(MODE === 'dev') {
    API_BASE_PATH = `http://localhost:${PORT}/`
    WS_BASE_PATH = `ws://localhost:${PORT}/`
}

if(MODE === 'prod') {
    API_BASE_PATH = 'api'
    WS_BASE_PATH = 'ws'
}
