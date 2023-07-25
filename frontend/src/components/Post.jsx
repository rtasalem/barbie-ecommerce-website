import React from 'react';

/* Post component displays info about items, passes item as prop and renders the name and description */
const Post = ({ item }) => {
  return (
    <article>
      <h2>{item.name}</h2>
      <p>{item.description}</p>
    </article>
  );
};

export default Post;