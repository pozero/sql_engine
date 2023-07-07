import MenuItem from '@mui/material/MenuItem';
import { TextField } from '@mui/material';

function RequestMethod(method, setMethod) {
    return (
        <TextField
            id="method-select"
            select
            value={method}
            label="request method"
            onChange={(e) => {
                setMethod(e.target.value)
            }}
        >
            <MenuItem value="GET">GET</MenuItem>
            <MenuItem value="POST">POST</MenuItem>
            <MenuItem value="DELETE">DELETE</MenuItem>
            <MenuItem value="PUT">PUT</MenuItem>
        </TextField>
    );
}

export default RequestMethod;