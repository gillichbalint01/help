import React from 'react'
import { Content } from '../components/Content'
import { Genres } from '../components/Genres'
import { useState } from 'react'
const urlSeries=`https://api.themoviedb.org/3/discover/tv?api_key=${import.meta.env.VITE_API_KEY}`

export const TVSeries = () => {
  const [urlForGenres,setUrlForGenres]=useState('')
  return (
    <div>
       <Genres type='tv' setUrlForGenres={setUrlForGenres}/>
       <Content url={urlSeries+'&with_genres='+urlForGenres} type='tv'/>
    </div>
  )
}


