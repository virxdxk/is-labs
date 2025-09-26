import React, { useState, useRef } from 'react'
import { Card } from 'primereact/card'
import { Button } from 'primereact/button'
import { InputText } from 'primereact/inputtext'
import { InputNumber } from 'primereact/inputnumber'
import { Toast } from 'primereact/toast'
import { DataTable } from 'primereact/datatable'
import { Column } from 'primereact/column'
import { classNames } from 'primereact/utils'

import MovieService from '../services/MovieService'

const SpecialOperationsPage = () => {
    const [tagline, setTagline] = useState('')
    const [goldenPalmCount, setGoldenPalmCount] = useState(0)
    const [result, setResult] = useState(null)
    const [directorsWithoutOscars, setDirectorsWithoutOscars] = useState([])
    const [errors, setErrors] = useState({})
    const toast = useRef(null)

    const resetStates = () => {
        setResult(null)
        setDirectorsWithoutOscars([])
        setErrors({})
    }

    const validateTagline = () => {
        const newErrors = {}
        if (!tagline || tagline.trim() === '') {
            newErrors.tagline = 'Слоган обязателен'
        }
        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    const validateGoldenPalmCount = () => {return true}

    const handleCountByTagline = async () => {
        if (!validateTagline()) return
        resetStates()
        try {
            const response = await MovieService.countByTagline(tagline)
            setResult(`Количество фильмов со слоганом "${tagline}": ${response.data.count}`)
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось выполнить операцию' })
        }
    }

    const handleCountLessThanGoldenPalm = async () => {
        if (!validateGoldenPalmCount()) return
        resetStates()
        try {
            const response = await MovieService.countLessThanGoldenPalm(goldenPalmCount)
            setResult(`Количество фильмов с количеством золотых пальм < ${goldenPalmCount}: ${response.data.count}`)
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось выполнить операцию' })
        }
    }

    const handleCountGreaterThanGoldenPalm = async () => {
        if (!validateGoldenPalmCount()) return
        resetStates()
        try {
            const response = await MovieService.countGreaterThanGoldenPalm(goldenPalmCount)
            setResult(`Количество фильмов с количеством золотых пальм > ${goldenPalmCount}: ${response.data.count}`)
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось выполнить операцию' })
        }
    }

    const handleGetDirectorsWithoutOscars = async () => {
        resetStates()
        try {
            const response = await MovieService.getDirectorsWithoutOscars()
            setDirectorsWithoutOscars(response.data.count)
            setResult('Режиссеры без Оскаров загружены')
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось выполнить операцию' })
        }
    }

    const handleAddOscarToRRatedMovies = async () => {
        resetStates()
        try {
            await MovieService.addOscarToRRatedMovies()
            toast.current.show({ severity: 'success', summary: 'Успех', detail: 'Оскары добавлены фильмам категории R' })
            setResult('Оскары добавлены фильмам категории R')
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось выполнить операцию' })
        }
    }

    return (
        <div>
            <Toast ref={toast} />
            <Card title="Специальные операции">
                <div className="p-fluid">
                    <div className="form-field">
                        <label>Слоган для подсчета:</label>
                        <InputText
                            value={tagline}
                            onChange={(e) => setTagline(e.target.value)}
                            className={classNames({ 'p-invalid': errors.tagline })}
                        />
                        {errors.tagline && <small className="p-error">{errors.tagline}</small>}
                        <Button label="Подсчитать фильмы по слогану" onClick={handleCountByTagline} className="mt-2" />
                    </div>

                    <div className="form-field">
                        <label>Количество золотых пальм для сравнения:</label>
                        <InputNumber
                            value={goldenPalmCount}
                            onValueChange={(e) => setGoldenPalmCount(e.value)}
                            min={0}
                            className={classNames({ 'p-invalid': errors.goldenPalmCount })}
                        />
                        {errors.goldenPalmCount && <small className="p-error">{errors.goldenPalmCount}</small>}
                        <Button
                            label="Подсчитать фильмы с меньшим количеством золотых пальм"
                            onClick={handleCountLessThanGoldenPalm}
                            className="mt-2 mr-2"
                        />
                        <Button
                            label="Подсчитать фильмы с большим количеством золотых пальм"
                            onClick={handleCountGreaterThanGoldenPalm}
                            className="mt-2"
                        />
                    </div>

                    <div className="form-field">
                        <Button
                            label="Получить режиссеров без Оскаров"
                            onClick={handleGetDirectorsWithoutOscars}
                        />
                    </div>

                    <div className="form-field">
                        <Button
                            label="Добавить Оскар фильмам категории R"
                            onClick={handleAddOscarToRRatedMovies}
                        />
                    </div>

                    {result && (
                        <div className="form-field">
                            <Card title="Результат">
                                <p>{result}</p>
                            </Card>
                        </div>
                    )}

                    {directorsWithoutOscars.length > 0 && (
                        <div className="form-field">
                            <Card title="Режиссеры без Оскаров">
                                <DataTable
                                    value={directorsWithoutOscars}
                                    emptyMessage="Нет режиссеров без Оскаров"
                                >
                                    <Column field="id" header="ID" />
                                    <Column field="name" header="Имя" />
                                    <Column field="weight" header="Вес" />
                                    <Column field="eyeColor" header="Цвет глаз" />
                                    <Column field="hairColor" header="Цвет волос" />
                                    <Column field="nationality" header="Национальность" />
                                    <Column field="location.x" header="X" />
                                    <Column field="location.y" header="Y" />
                                    <Column field="location.z" header="Z" />
                                </DataTable>
                            </Card>
                        </div>
                    )}
                </div>
            </Card>
        </div>
    )
}

export default SpecialOperationsPage