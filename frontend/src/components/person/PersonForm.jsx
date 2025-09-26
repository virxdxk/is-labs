import React, { useState, useEffect } from 'react'
import { Button } from 'primereact/button'
import { InputText } from 'primereact/inputtext'
import { InputNumber } from 'primereact/inputnumber'
import { Dropdown } from 'primereact/dropdown'
import { TabView, TabPanel } from 'primereact/tabview'
import { classNames } from 'primereact/utils'
import {COLORS, COUNTRIES} from "../../domain/values.js";
import {validateMovieForm, validatePersonForm} from "../../utils/validation.js";

const PersonForm = ({ person, onSubmit, onCancel, loading }) => {
    const [formData, setFormData] = useState({
        name: '',
        weight: 0,
        eyeColor: null,
        hairColor: null,
        nationality: null,
        location: { x: 0, y: 0, z: 0 }
    })

    const [errors, setErrors] = useState({})

    useEffect(() => {
        if (person) {
            setFormData({
                name: person.name || '',
                weight: person.weight || 0,
                eyeColor: person.eyeColor || null,
                hairColor: person.hairColor || null,
                nationality: person.nationality || null,
                location: person.location || { x: 0, y: 0, z: 0 }
            })
        }
    }, [person])

    const validateForm = () => {
        const newErrors = validatePersonForm(formData)
        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        if (validateForm()) {
            onSubmit(formData)
        }
    }

    const handleInputChange = (field, value) => {
        setFormData(prev => ({ ...prev, [field]: value }))
    }

    const handleLocationChange = (field, value) => {
        setFormData(prev => ({
            ...prev,
            location: { ...prev.location, [field]: value }
        }))
    }

    return (
        <form onSubmit={handleSubmit}>
            <TabView>
                <TabPanel header="Основная информация">
                    <div className="form-field">
                        <label>Имя *</label>
                        <InputText
                            value={formData.name}
                            onChange={(e) => handleInputChange('name', e.target.value)}
                            className={classNames({ 'p-invalid': errors.name })}
                            style={{ width: '100%' }}
                        />
                        {errors.name && <small className="p-error">{errors.name}</small>}
                    </div>

                    <div className="form-field">
                        <label>Вес *</label>
                        <InputNumber
                            value={formData.weight}
                            onValueChange={(e) => handleInputChange('weight', e.value)}
                            className={classNames({ 'p-invalid': errors.weight })}
                            min={0.01}
                            style={{ width: '100%' }}
                        />
                        {errors.weight && <small className="p-error">{errors.weight}</small>}
                    </div>

                    <div className="form-field">
                        <label>Цвет глаз</label>
                        <Dropdown
                            value={formData.eyeColor}
                            options={COLORS}
                            onChange={(e) => handleInputChange('eyeColor', e.value)}
                            placeholder="Выберите цвет глаз"
                            style={{ width: '100%' }}
                        />
                    </div>

                    <div className="form-field">
                        <label>Цвет волос *</label>
                        <Dropdown
                            value={formData.hairColor}
                            options={COLORS}
                            onChange={(e) => handleInputChange('hairColor', e.value)}
                            className={classNames({ 'p-invalid': errors.hairColor })}
                            placeholder="Выберите цвет волос"
                            style={{ width: '100%' }}
                        />
                        {errors.hairColor && <small className="p-error">{errors.hairColor}</small>}
                    </div>

                    <div className="form-field">
                        <label>Национальность</label>
                        <Dropdown
                            value={formData.nationality}
                            options={COUNTRIES}
                            onChange={(e) => handleInputChange('nationality', e.value)}
                            placeholder="Выберите национальность"
                            style={{ width: '100%' }}
                        />
                    </div>
                </TabPanel>

                <TabPanel header="Локация">
                    <div className="form-field">
                        <label>X *</label>
                        <InputNumber
                            value={formData.location?.x || 0}
                            onValueChange={(e) => handleLocationChange('x', e.value)}
                            style={{ width: '100%' }}
                        />
                    </div>

                    <div className="form-field">
                        <label>Y *</label>
                        <InputNumber
                            value={formData.location?.y || 0}
                            onValueChange={(e) => handleLocationChange('y', e.value)}
                            style={{ width: '100%' }}
                        />
                    </div>

                    <div className="form-field">
                        <label>Z *</label>
                        <InputNumber
                            value={formData.location?.z || 0}
                            onValueChange={(e) => handleLocationChange('z', e.value)}
                            style={{ width: '100%' }}
                        />
                    </div>
                </TabPanel>
            </TabView>

            <div className="form-field mt-3">
                <Button type="submit" label="Сохранить" loading={loading} className="mr-2" />
                <Button type="button" label="Отмена" onClick={onCancel} className="p-button-secondary" />
            </div>
        </form>
    )
}

export default PersonForm