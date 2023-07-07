import { TextField } from '@mui/material';
import MenuItem from '@mui/material/MenuItem';
import React from 'react';

function DatabaseSelectName(dbName, setDbName) {
    const [databases, setDatabases] = React.useState([]);
    React.useEffect(() => {
        fetch("http://localhost:8080/allDbInfo")
            .then(res => res.json())
            .then((result) => {
                setDatabases(result)
            })
    })
    const databaseList = databases.map((database) => (
        <MenuItem value={database}>{database}</MenuItem>
    ))
    return (
        <TextField
            id="dbname-select"
            select
            value={dbName}
            label="database name"
            onChange={(e) => {
                setDbName(e.target.value)
            }}>
            {databaseList}
        </TextField>
    );
}

export default DatabaseSelectName;