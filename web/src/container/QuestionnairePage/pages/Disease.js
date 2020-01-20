import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { changeDisease, nextPage } from '../actions';
import { Icon, Button } from 'antd';
import { tags } from '../constant_options';
import { Container } from './Name';
import { OptionsContainer, Option, CheckedIcon, OptionContainer } from './BloodType';
import PageControllContainer from '../PageControllContainer';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

const Note = styled.span`
  font-size: 12px;
  color: #aaa;
`;
//#endregion

function Disease(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>疾病歷史</span>
        <Note>(可複選)</Note>
      </div>
      <OptionsContainer>
        {tags
          .filter(t => t.jhi_type === 'DISEASE')
          .map(d => {
            const selected = props.disease.includes(d.key);
            return (
              <OptionContainer
                key={d.key}
                onClick={() => {
                  props.changeDisease(d.key);
                }}
                selected={selected}
              >
                <Option selected={selected}>{d.key}</Option>
                <span>{d.value}</span>
                <CheckedIcon type="check" selected={selected} />
              </OptionContainer>
            );
          })}
      </OptionsContainer>
      <Button type="primary" onClick={props.nextPage}>
        <span>
          確認
          <Icon type="check" />
        </span>
      </Button>
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ disease: state.questionnairePageReducer.data.patient.disease });

const mapDispatchToProps = { changeDisease, nextPage };

export default connect(mapStateToProps, mapDispatchToProps)(Disease);
