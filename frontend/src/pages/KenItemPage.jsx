import React from 'react';
import KenItemCard from '../components/KenItemCard';
import KenItemGrid from '../components/KenItemGrid';

const KenItemPage = () => {
    return (
        <div>
            <div className="item-page">
                <KenItemCard />
            </div>
            <KenItemGrid />
        </div>
    );
};

export default KenItemPage;