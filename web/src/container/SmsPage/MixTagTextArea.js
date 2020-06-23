import React, { useEffect, useState } from 'react';
import { createGlobalStyle } from 'styled-components';
import Tags from '@yaireo/tagify/dist/react.tagify';
import '@yaireo/tagify/dist/tagify.css';
import { tags } from './constant';

const GlobalStyle = createGlobalStyle`
  body .tagify {
    --tag-bg: #3266ff;
    --tag-text-color: #fff;
    --tag-text-color--edit: white;
    --tag-hover: rgba(255, 38, 68, 0.5);
    --tag--min-width: 50px;
    --tag-pad: 0.6em 1em;
  }

  .tagify.tagify--mix.myTags {
    font-size: 15px!important;
    height: 200px!important;
    border-radius: 8px!important;
    overflow-y: scroll;
    scrollbar-width: none;
  }

  .tagify.tagify--mix.myTags::-webkit-scrollbar{
    display: none;
  }

  .tagify__tag.tagify--noAnim {
    margin: 2px 5px!important;
  }

  .tagify__tag > div {
    border-radius: 25px!important;
  }
`;

const settings = {
  enforceWhitelist: true,
  mode: 'mix',
  pattern: /@/,
  mixTagsInterpolator: ['{{', '}}'],
  addTagOnBlur: true,
  editTags: 0,
  dropdown: {
    enabled: 0,
    position: 'text',
    highlightFirst: true,
  },
  duplicates: true,
  callbacks: {},
  whitelist: tags.map(t => ({ value: t })),
};

function MixTagTextArea({ value, onChange, id }) {
  const [show, setShow] = useState(false);

  useEffect(() => {
    setShow(false);
    requestAnimationFrame(() => {
      setShow(true);
    });
  }, [id]);

  const onTextChange = e => {
    let text = e.target.value;
    settings.whitelist.forEach(w => {
      const regex = new RegExp(JSON.stringify({ value: w.value }), 'g');
      const regex2 = new RegExp(JSON.stringify({ value: w.value, prefix: '@' }), 'g');
      text = text.replace(regex, w.value);
      text = text.replace(regex2, w.value);
    });
    const event = { target: { value: text } };
    onChange(event);
  };

  return (
    <div className="tagContainer">
      <GlobalStyle />
      {show && (
        <Tags
          mode="textarea"
          settings={settings}
          className="myTags"
          onChange={onTextChange}
          placeholder="填寫簡訊寄送內容，至多 70 字"
          autoSize={{ minRows: 6, maxRows: 6 }}
          value={value}
        />
      )}
    </div>
  );
}

export default MixTagTextArea;
