import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/is-labs-1.0/people'

class PersonService {
    constructor() {
        this.api = axios.create({
            baseURL: API_BASE_URL,
            timeout: 10000,
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    getPeople(lazyState) {
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
        return this.api.get('/', { params })
    }

    getPerson(id) {
        return this.api.get(`/${id}`)
    }

    createPerson(person) {
        return this.api.post('/', person)
    }

    updatePerson(id, person) {
        return this.api.patch(`/${id}`, person)
    }

    deletePerson(id) {
        return this.api.delete(`/${id}`)
    }
}

export default new PersonService()