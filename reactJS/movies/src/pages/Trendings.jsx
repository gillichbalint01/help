import React from 'react'
import { Content } from '../components/Content'
const urlTrending = `https://api.themoviedb.org/3/trending/all/day?api_key=${import.meta.env.VITE_API_KEY}&include_adult=false`


export const Trendings = () => {
  return (
    <div>
         <Content url={urlTrending}/>
    </div>
  )
}

