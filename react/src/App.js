import './App.css';
import { Sidebar, Menu, MenuItem, useProSidebar } from 'react-pro-sidebar';

import * as React from 'react';

import MenuOutlinedIcon from "@mui/icons-material/MenuOutlined";

import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Paper } from '@mui/material';
import Button from '@mui/material/Button';

import RequestMethod from "./components/RequestMethod";
import DatabaseSelectId from './components/DatabaseSelectId';
import DatabaseSelectName from './components/DatabaseSelectName';

function App() {
  const { collapseSidebar } = useProSidebar();

  const [url, setUrl] = React.useState('');
  const [method, setMethod] = React.useState('');
  const [dbId, setDbid] = React.useState('');
  const [userSql, setSql] = React.useState('');
  const [apis, setApis] = React.useState([]);

  const [dbName, setDbName] = React.useState('');
  const [dbAddr, setDbAddr] = React.useState('');
  const [user, setUser] = React.useState('');
  const [password, setPassword] = React.useState('');

  const paperStyle = { padding: '50px 20px', width: 600, margin: 'auto auto' }

  React.useEffect(() => {
    fetch("http://localhost:8080/url/sql")
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
        setSql(api.userSql);
      }}>
      {api.url} {api.method}<br />
      {api.dbId} {api.userSql}

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
        <h2><u>生成Api接口</u></h2>
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
          {RequestMethod(method, setMethod)}
          {DatabaseSelectId(dbId, setDbid)}
          <TextField
            id="outlined-multiline-static"
            label="SQL Command"
            multiline
            rows={4}
            value={userSql}
            onChange={(e) => setSql(e.target.value)}
          />
          <Button variant="contained" onClick={(e) => {
            e.preventDefault()
            const api = { url, method, userSql, dbId }
            console.log(JSON.stringify(api))
            fetch("http://localhost:8080/url/sql",
              {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(api)
              }).then(() => {
                console.log("New api added")
              })
          }
          }>
            提交
          </Button>
          <Button variant='contained' onClick={(e) => {
            e.preventDefault();
            console.log(url, method);
            fetch("http://localhost:8080/url/sql",
              {
                method: "DELETE",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ "url": url, "method": method })
              }).then(() => {
                console.log("Api Deleted")
              })
          }}>
            删除
          </Button>
        </Box>
      </Paper>

      <Paper elevation={3} style={paperStyle}>
        <h2><u>添加数据库</u></h2>
        <Box
          component="form"
          sx={{
            '& > :not(style)': { m: 1, width: '25ch' },
          }}
          noValidate
          autoComplete="off"
        >
          {DatabaseSelectName(dbName, setDbName)}
          <TextField id="outlined-basic" label="database address" variant="outlined" fullWidth
            value={dbAddr}
            onChange={(e) => setDbAddr(e.target.value)} />
          <TextField id="outlined-basic" label="user" variant="outlined" fullWidth
            value={user}
            onChange={(e) => setUser(e.target.value)} />
          <TextField id="outlined-basic" label="password" variant="outlined" fullWidth type='password'
            value={password}
            onChange={(e) => setPassword(e.target.value)} />
        </Box>
        <Button variant="contained" onClick={(e) => {
          e.preventDefault();
          const dbInfo = { dbName, dbAddr, user, password };
          console.log(dbInfo);
          fetch("http://localhost:8080/datasource",
            {
              "method": "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify(dbInfo)
            }
          );
        }}>
          提交
        </Button>
      </Paper>
    </div>
  );
}

export default App;
