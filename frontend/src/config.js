const MODE = 'prod'

export let API_BASE_PATH;
export let WS_BASE_PATH;

if(MODE === 'dev') {
    API_BASE_PATH = 'http://localhost:8080/is-labs-1.0'
    WS_BASE_PATH = 'ws://localhost:8080/is-labs-1.0'
}

if(MODE === 'prod') {
    API_BASE_PATH = 'api'
    WS_BASE_PATH = 'ws'
}
