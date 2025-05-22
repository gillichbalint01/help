import { useQuery } from '@tanstack/react-query';
import React from 'react'
import AliceCarousel from 'react-alice-carousel';
import 'react-alice-carousel/lib/alice-carousel.css';
import { getData, img_300, noPicture } from '../utils';
import { CircularProgress } from '@mui/material';

export const Carousel = ({id,media_type}) => {
    const urlCredits=`https://api.themoviedb.org/3/${media_type}/${id}/credits?api_key=${import.meta.env.VITE_API_KEY}`;

    const {data,isError,isLoading,error}=useQuery({queryKey:['credits',urlCredits],queryFn:getData})

    
    if(isLoading) return <CircularProgress />

    if(isError) return <p>Error fetching data : {error.message}</p>

    console.log(data.cast);

    const items=data.cast.map(obj=>(
        <div className='carousel_container'>
            <img className='carousel_img' src={obj.profile_path? img_300+obj.profile_path : noPicture} alt={obj?.name}  />
            <b className='carousel_name'>{obj?.name}</b>
        </div>
    ))

   const responsive={
    0:{items:3},
    512:{items:5},
    1024:{items:7}
   } 
  return (
    <div>
      <AliceCarousel 
        mouseTracking
        autoPlay
        infinite
        disableButtonsControls
        disableDotsControls
        responsive={responsive}
        items={items}
      />
    </div>
  )
}

