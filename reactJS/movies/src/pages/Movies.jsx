import React from 'react'
import { Content } from '../components/Content'
import { Genres } from '../components/Genres'
import { useState } from 'react'
const urlMovies=`https://api.themoviedb.org/3/discover/movie?api_key=${import.meta.env.VITE_API_KEY}`

export const Movies = () => {
  const [urlForGenres,setUrlForGenres]=useState('')
  return (
    <div>
       <Genres type='movie' setUrlForGenres={setUrlForGenres} />
       <Content url={urlMovies+'&with_genres='+urlForGenres} type='movie'/>
    </div>
  )
}


