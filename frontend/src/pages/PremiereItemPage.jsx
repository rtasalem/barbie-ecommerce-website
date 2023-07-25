import React from 'react';
import PremiereItemCard from '../components/PremiereItemCard';
import PremiereItemGrid from '../components/PremiereItemGrid';

const PremiereItemPage = () => {
    return (
        <div>
            <div className="item-page">
                <PremiereItemCard />
            </div>
            <PremiereItemGrid />
        </div>
    );
};

export default PremiereItemPage;