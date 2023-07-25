import React from 'react';
import SwimwearItemCard from '../components/SwimwearItemCard';
import SwimwearItemGrid from '../components/SwimwearItemGrid';

const SwimwearItemPage = () => {
    return (
        <div>
            <div className="item-page">
                <SwimwearItemCard />
            </div>
            <SwimwearItemGrid />
        </div>
    );
};

export default SwimwearItemPage;