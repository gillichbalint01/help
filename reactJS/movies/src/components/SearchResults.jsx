import React,{useState} from "react";
import { getData } from "../utils";
import {useQuery} from '@tanstack/react-query'
import SingleContent from "./SingleContent";
import { ContentPagination } from "./ContentPagination";

export const SearchResults = ({searchText,type}) => {
  //console.log('type:',type);
    const [page,setPage] = useState(1);
  const urlSearch = `https://api.themoviedb.org/3/search/${type}?api_key=${import.meta.env.VITE_API_KEY}&include_adult=false&query=${searchText}&page=${page}`;
  const { data, status } = useQuery({queryKey:["searchedItems", urlSearch],queryFn: getData});
  status == "success" && console.log(data.results, data.total_pages);
  return (
    <div className="mainContent">
      {status == "success" &&
           data.results.map((obj) => (
          <SingleContent key={obj.id} {...obj} type={type}  />
        ))
        }
      {status == "success" && data.results.length > 0 &&
        <ContentPagination  page={page} setPage={setPage} numberOfPage={10}/>
      }
      {status == "success" &&
        data.results.length == 0 &&
        (type=='tv' ? <h2>No Series Found</h2> : <h2>No Movies Found</h2>)}
    </div>
  );
};