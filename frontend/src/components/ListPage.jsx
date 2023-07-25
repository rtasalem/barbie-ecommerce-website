import React from 'react';
import Post from './Post';
import '../styles/ListPage.css';

const ListPage = ({ searchResults }) => {   /* searchResult = prop */
  const hasSearchQuery = searchResults.length > 0; /* checks that the searchResult entered into the text input has more than 0 characters */
  const content = hasSearchQuery ? (    /* the variable is set to true is the above statement is true */
    searchResults.map((item) => <Post key={item.itemId} item={item} />)
  ) 
  : ([]);

  return <main>{content}</main>;
};

export default ListPage;

/* ListPage takes the searchResults prop and checks if there are any search results. Then renders the Post component */ 
