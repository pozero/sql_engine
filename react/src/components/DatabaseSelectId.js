import { TextField } from '@mui/material';
import MenuItem from '@mui/material/MenuItem';
import React from 'react';

function DatabaseSelectId(dbId, setdbId) {
    const [databases, setDatabases] = React.useState([]);
    React.useEffect(() => {
        fetch("http://localhost:8080/dbInfo")
            .then(res => res.json())
            .then((result) => {
                setDatabases(result)
            })
    })
    const databaseList = databases.map((database) => (
        <MenuItem value={database.id}>{database.dbName + " - " + database.user}</MenuItem>
    ))
    return (
        <TextField
            id="dbid-select"
            select
            value={dbId}
            label="database name"
            onChange={(e) => {
                setdbId(e.target.value)
            }}>
            {databaseList}
        </TextField>
    );
}

export default DatabaseSelectId;