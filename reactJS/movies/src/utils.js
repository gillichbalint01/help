import axios from 'axios'

export const img_300='https://image.tmdb.org/t/p/w300';
export const img_500='https://image.tmdb.org/t/p/w500';
export const imgUnavailable='https://www.movienewz.com/img/films/poster-holder.jpg';
export const noPicture='https://upload.wikimedia.org/wikipedia/en/6/60/No_Picture.jpg';
export const noPictureLandscape='https://upload.wikimedia.org/wikipedia/commons/a/a6/No_picture_available_png.png'

export const getData= async ({queryKey}) =>{
    // console.log(queryKey);//tömb lesz
    // console.log(queryKey[1]); //itt lesz az
    const url = queryKey[1];
    const response = await axios.get(url);
    return response.data
    
}
