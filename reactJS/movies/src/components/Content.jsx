import { useQuery } from '@tanstack/react-query'
import React from 'react'
import { getData } from '../utils'
import { CircularProgress } from '@mui/material'
import SingleContent from './SingleContent'
import { useState } from 'react'
import { ContentPagination } from './ContentPagination'

export const Content = ({url,type}) => {
    const [page,setPage]=useState(1)
    const {isLoading,isError,error,data}=useQuery({queryKey:['trendings',url+"&page="+page], queryFn:getData})

    if(isLoading) return <CircularProgress />

    if(isError) return <p>Error fetching data : {error.message}</p>
    console.log(data.results,data.total_pages);
    
    

  return (
    <div className='mainContent'>
        {data.results.map(obj=>
            <SingleContent key={obj.id} {...obj} type={type}/>
        )}
        <ContentPagination page={page} setPage={setPage} numberOfPage={data.total_pages}/>
    </div>
  )
}


