import { useQuery } from '@tanstack/react-query'
import React from 'react'
import { getData } from '../utils'
import { CircularProgress } from '@mui/material'
import { SingleChip } from './SingleChip'
import Stack from '@mui/material/Stack';
import { useState } from 'react'
import { useEffect } from 'react'

export const Genres = ({type,setUrlForGenres}) => {
    const [selectedGenres,setSelectedGenres]=useState([])/*kategóriák azonosítói amit a felhasználó kiválaszt */
console.log(selectedGenres);

useEffect(()=>{
  if(selectedGenres.length<1) setUrlForGenres('')
  else setUrlForGenres(selectedGenres.join(','))
},[selectedGenres])

    const urlGenres=`https://api.themoviedb.org/3/genre/${type}/list?api_key=${import.meta.env.VITE_API_KEY}`
//console.log(urlGenres);
    const {data,isError,isLoading,error}=useQuery({queryKey:['genres',urlGenres],queryFn:getData})
    
    if(isLoading) return <CircularProgress />
    if(isError) return <p>Error fetching data : {error.message}</p>
    
    //console.log(data.genres);
  return (
    <div>
    <Stack direction="row" spacing={1} sx={{display:'flex',flexWrap:'wrap',justifyContent:'center',padding:'10px'}}>
        {data.genres.map(obj=>
            <SingleChip key={obj.id} {...obj} selectedGenres={selectedGenres} setSelectedGenres={setSelectedGenres}/>)}
    </Stack>
    </div>
  )
}


