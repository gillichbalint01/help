import * as React from 'react';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';

export const ContentPagination=({page,setPage,numberOfPage=10})=> {

    const handleChange=(event,p)=>{
        console.log('oldal:',p);
        setPage(p)
    }
  return (
    <Stack spacing={2} sx={{width:'100%'}}>
       <Pagination count={numberOfPage} variant="outlined" shape="rounded"
        page={page}
        onChange={handleChange}
       />
    </Stack>
  );
}
