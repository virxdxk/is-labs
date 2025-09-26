import React, {useEffect, useRef, useState} from 'react'
import {Card} from 'primereact/card'
import {Button} from 'primereact/button'
import {ConfirmDialog, confirmDialog} from 'primereact/confirmdialog'
import {Toast} from 'primereact/toast'
import {Dialog} from 'primereact/dialog'
import PersonService from "../../services/PersonService.jsx";
import PersonForm from "../../components/person/PersonForm.jsx";
import PersonTable from "../../components/person/PersonTable.jsx";

const PersonListPage = () => {
    const [people, setPeople] = useState([])
    const [loading, setLoading] = useState(false)
    const [totalRecords, setTotalRecords] = useState(100)
    const [lazyState, setLazyState] = useState({
        first: 0,
        rows: 10,
        sortField: null,
        sortOrder: null,
        filters: {}
    });
    const [selectedPerson, setSelectedPerson] = useState(null)
    const [createDialogVisible, setCreateDialogVisible] = useState(false)
    const [editDialogVisible, setEditDialogVisible] = useState(false)
    const toast = useRef(null)

    useEffect(() => {
        loadPeople()
    }, [lazyState])

    const loadPeople = async () => {
        setLoading(true)
        try {
            const response = await PersonService.getPeople(lazyState)
            setPeople(response.data.data)
            setTotalRecords(response.data.totalRecords)
        } catch (error) {
            toast.current.show({severity: 'error', summary: 'Ошибка', detail: 'Не удалось загрузить людей'})
        } finally {
            setLoading(false)
        }
    }

    const handleCreate = () => {
        setCreateDialogVisible(true)
    }

    const handleEdit = () => {
        if (selectedPerson) {
            setEditDialogVisible(true)
        }
    }

    const handleDelete = () => {
        if (selectedPerson) {
            confirmDialog({
                message: 'Вы уверены, что хотите удалить этого человека?',
                header: 'Подтверждение удаления',
                icon: 'pi pi-exclamation-triangle',
                accept: () => deletePerson(selectedPerson.id)
            })
        }
    }

    const deletePerson = async (id) => {
        try {
            await PersonService.deletePerson(id)
            loadPeople()
            setSelectedPerson(null)
            toast.current.show({severity: 'success', summary: 'Успех', detail: 'Человек удален'})
        } catch (error) {
            toast.current.show({
                severity: 'error',
                summary: 'Ошибка',
                detail: error.response?.data?.message || 'Не удалось удалить человека'
            })
        }
    }

    const onCreateSuccess = async (personData) => {
        try {
            await PersonService.createPerson(personData)
            setCreateDialogVisible(false)
            loadPeople()
            toast.current.show({severity: 'success', summary: 'Успех', detail: 'Человек создан'})
        } catch (error) {
            toast.current.show({
                severity: 'error',
                summary: 'Ошибка',
                detail: error.response?.data?.message || 'Не удалось создать человека'
            })
        }
    }

    const onEditSuccess = async (personData) => {
        try {
            await PersonService.updatePerson(selectedPerson.id, personData)
            setEditDialogVisible(false)
            loadPeople()
            toast.current.show({severity: 'success', summary: 'Успех', detail: 'Человек обновлен'})
        } catch (error) {
            toast.current.show({
                severity: 'error',
                summary: 'Ошибка',
                detail: error.response?.data?.message || 'Не удалось обновить человека'
            })
        }
    }

    const handleSelectionChange = (e) => {
        setSelectedPerson(e.value)
    }

    const handleLazyStateChange = (e) => {
        setLazyState(e);
    }

    return (
        <div>
            <Toast ref={toast}/>
            <ConfirmDialog/>

            {/* Диалог создания */}
            <Dialog
                header="Создать человека"
                visible={createDialogVisible}
                style={{width: '50vw'}}
                onHide={() => setCreateDialogVisible(false)}
            >
                <PersonForm
                    onSubmit={onCreateSuccess}
                    onCancel={() => setCreateDialogVisible(false)}
                    loading={loading}
                />
            </Dialog>

            {/* Диалог редактирования */}
            <Dialog
                header="Редактировать человека"
                visible={editDialogVisible}
                style={{width: '50vw'}}
                onHide={() => setEditDialogVisible(false)}
            >
                <PersonForm
                    person={selectedPerson}
                    onSubmit={onEditSuccess}
                    onCancel={() => setEditDialogVisible(false)}
                    loading={loading}
                />
            </Dialog>

            <Card title="Список людей">
                <div className="mb-3">
                    <Button label="Создать" icon="pi pi-plus" onClick={handleCreate}/>
                    <Button
                        label="Изменить"
                        icon="pi pi-pencil"
                        onClick={handleEdit}
                        className="ml-2"
                        disabled={!selectedPerson}
                    />
                    <Button
                        label="Удалить"
                        icon="pi pi-trash"
                        onClick={handleDelete}
                        className="ml-2 p-button-danger"
                        disabled={!selectedPerson}
                    />
                </div>

                <PersonTable
                    people={people}
                    loading={loading}
                    lazyState={lazyState}
                    setLazyState={setLazyState}
                    totalRecords={totalRecords}
                    selection={selectedPerson}
                    onSelectionChange={handleSelectionChange}
                />
            </Card>
        </div>
    )
}

export default PersonListPage