import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { useQuery } from '@tanstack/react-query';
import {getData} from '../utils'
import { Button, CircularProgress } from '@mui/material';
import { img_500 } from '../utils';
import { noPictureLandscape } from '../utils';
import { Carousel } from './Carousel';
import YouTubeIcon from '@mui/icons-material/YouTube';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  maxWidth: 600,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
  display:'flex',
  flexDirection:'column'
};

export const DetailModal=({open,setOpen,id,media_type})=> {
    const urlDetails=`https://api.themoviedb.org/3/${media_type}/${id}?api_key=${import.meta.env.VITE_API_KEY}`;
    const urlVideos=`https://api.themoviedb.org/3/${media_type}/${id}/videos?api_key=${import.meta.env.VITE_API_KEY}`;

    const {data,isLoading,isError,error}=useQuery({queryKey:['details',urlDetails],queryFn:getData})
    const {data:dataVideos,isLoading:isLoadingVideos,isError:isErrorVideos,error:errorVideos}=useQuery({queryKey:['videos',urlVideos],queryFn:getData})

    
    if(isLoading || isLoadingVideos) return <CircularProgress />

    if(isError || isErrorVideos) return <p>Error fetching data : {error.message||errorVideos.message}</p>

    console.log(data);
    console.log(dataVideos?.results);
    
    
  const handleClose = () => setOpen(false);

  return (
    <div>
     
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <img style={{width:'100%'}} src={data.backdrop_path? img_500+data.backdrop_path : noPictureLandscape} alt={data?.title||data?.name} />
          <Typography id="modal-modal-description" sx={{ mt: 2,display:'flex',flexDirection:'column',alignItems:'center' }}>
            <span > <b>{data?.title||data?.name}</b> {data?.release_date || data?.first_air_date }</span>
            <span style={{color:'blue',fontStyle:'italic'}}>{data?.tagline}</span>
            <span>{data?.overview}</span>
          </Typography>
          <Carousel id={id} media_type={media_type}/>
          {dataVideos?.results && dataVideos?.results.length>0 &&
            <div>
                <Button
                    className="video"
                    variant='contained'
                    startIcon={<YouTubeIcon/>}
                    target='_blank'
                    href={`https://www.youtube.com/watch?v=${dataVideos?.results[0].key}`}
                >    
                Watch the trailer
                </Button>
            </div>
          }
        </Box>
      </Modal>
    </div>
  );
}
