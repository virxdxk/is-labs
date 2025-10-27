import React from 'react'
import { Link } from 'react-router-dom'
import { Menubar } from 'primereact/menubar'

const Header = () => {
    const items = [
        {
            label: 'Фильмы',
            icon: 'pi pi-video',
            template: (item) => (
                <Link to="/movies" className="p-menuitem-link">
                    <span className={item.icon}></span>
                    <span className="mx-2">{item.label}</span>
                </Link>
            )
        },
        {
            label: 'Люди',
            icon: 'pi pi-users',
            template: (item) => (
                <Link to="/people" className="p-menuitem-link">
                    <span className={item.icon}></span>
                    <span className="mx-2">{item.label}</span>
                </Link>
            )
        },
        {
            label: 'Специальные операции',
            icon: 'pi pi-cog',
            template: (item) => (
                <Link to="/special-operations" className="p-menuitem-link">
                    <span className={item.icon}></span>
                    <span className="mx-2">{item.label}</span>
                </Link>
            )
        }
    ]

    const start = (
        <div className="flex align-items-center">
            <i className="pi pi-film text-2xl mr-2" style={{ color: '#ff6b6b' }}></i>
            <span className="text-xl font-bold" style={{ color: '#ffffff' }}>Cinema Database</span>
        </div>
    )

    return (
        <div className="card mb-3">
            <Menubar model={items} start={start} />
        </div>
    )
}

export default Header