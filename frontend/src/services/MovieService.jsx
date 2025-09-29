import axios from 'axios'
import {API_BASE_PATH} from "../config.js";

const API_BASE_URL = `${API_BASE_PATH}/movies`

class MovieService {
    constructor() {
        this.api = axios.create({
            baseURL: API_BASE_URL,
            timeout: 10000,
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    getMovies(lazyState) {
        const params = new URLSearchParams();

        params.append('first', lazyState.first);
        params.append('pageSize', lazyState.rows);
        if (lazyState.sortField) {
            params.append('sortField', lazyState.sortField);
            params.append('sortOrder', lazyState.sortOrder);
        }
        if (lazyState.filters) {
            for (const [field, filter] of Object.entries(lazyState.filters)) {
                if (filter.value) {
                    params.append(field, filter.value);
                }
            }
        }

        return this.api.get('/', {params})
    }

    getMovie(id) {
        return this.api.get(`/${id}`)
    }

    createMovie(movie) {
        return this.api.post('/', movie)
    }

    updateMovie(id, movie) {
        return this.api.patch(`/${id}`, movie)
    }

    deleteMovie(id) {
        return this.api.delete(`/${id}`)
    }

    countByTagline(tagline) {
        return this.api.get(`/count-by-tagline/${tagline}`)
    }

    countLessThanGoldenPalm(count) {
        return this.api.get(`/count-less-than-golden-palm/${count}`)
    }

    countGreaterThanGoldenPalm(count) {
        return this.api.get(`/count-greater-than-golden-palm/${count}`)
    }

    getDirectorsWithoutOscars() {
        return this.api.get('/directors-without-oscars')
    }

    addOscarToRRatedMovies() {
        return this.api.patch('/add-oscar-to-r-rated')
    }
}

export default new MovieService()