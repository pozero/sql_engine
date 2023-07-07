import './App.css';
import { Sidebar, Menu, MenuItem, useProSidebar } from 'react-pro-sidebar';

import * as React from 'react';

import MenuOutlinedIcon from "@mui/icons-material/MenuOutlined";
import DeleteIcon from '@mui/icons-material/Delete';

import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container, Paper } from '@mui/material';
import Button from '@mui/material/Button';

function App() {
  const { collapseSidebar } = useProSidebar();

  const [url, setUrl] = React.useState('');
  const [method, setMethod] = React.useState('');
  const [dbId, setDbid] = React.useState('');
  const [sql, setSql] = React.useState('');
  const [apis, setApis] = React.useState('');
  const paperStyle = { padding: '50px 20px', width: 600, margin: 'auto auto' }

  const addClick = (e) => {
    e.preventDefault()
    const api = { url, method, sql, dbId }
    console.log(api)
    fetch("http://localhost:8080/api/sql",
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(api)
      }).then(() => {
        console.log("New api added")
      })
  }
  React.useEffect(() => {
    fetch("http://localhost:8080/api/sql")
      .then(res => res.json())
      .then((result) => {
        setApis(result)
      })
  })
  const apiList = apis.map(api => (
    <MenuItem
      onClick={() => {
        setUrl(api.url);
        setMethod(api.method);
        setDbid(api.dbId);
        setSql(api.sql);
      }}>
      {api.url} {api.method}<br />
      {api.dbId} {api.userSql}
      <Button icon={<DeleteIcon />} onClick={(e) => {
        e.preventDefault();
        console.log(api.url, api.method);
        fetch("http://localhost:8080/api/sql",
          {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: { "url": api.url, "method": api.method }
          }).then(() => {
            console.log("Api Deleted")
          })
      }}>
      </Button>
    </MenuItem>
  ))
  return (
    <div className="app" style={({ height: "100vh" }, { display: "flex" })}>
      <Sidebar style={{ height: "100vh" }} backgroundColor='rgb(150, 150, 150)'>
        <Menu>
          <MenuItem icon={<MenuOutlinedIcon />}
            onClick={() => {
              collapseSidebar();
            }}
            style={{ textAlign: "center" }}>
            {" "}
          </MenuItem>
          {apiList}
        </Menu>
      </Sidebar>
      <main>
        <h1 style={{ marginLeft: "5rem" }}>
          轻量级取数服务
        </h1>
      </main>

      <Paper elevation={3} style={paperStyle}>
        <h2><u>编辑映射</u></h2>
        <Box
          component="form"
          sx={{
            '& > :not(style)': { m: 1, width: '25ch' },
          }}
          noValidate
          autoComplete="off"
        >
          <TextField id="outlined-basic" label="url" variant="outlined" fullWidth
            value={url}
            onChange={(e) => setUrl(e.target.value)} />
          <TextField id="outlined-basic" label="method" variant="outlined" fullWidth
            value={method}
            onChange={(e) => setMethod(e.target.value)} />
          <TextField id="outlined-basic" label="database id" variant="outlined" fullWidth
            value={dbId}
            onChange={(e) => setDbid(e.target.value)} />
          <TextField
            id="outlined-multiline-static"
            label="SQL Command"
            multiline
            rows={4}
            value={sql}
            onChange={(e) => setSql(e.target.value)}
          />
          <Button variant="contained" onClick={addClick}>
            提交
          </Button>
        </Box>
      </Paper>
    </div>
  );
}

export default App;
