import { ethers } from "ethers";
import fs from "fs";
import path from "path";

async function main() {
  const outputDir = path.join(__dirname, "../../deployment_data");

  // 컨트랙트 데이터 읽기
  const fiatTokenBytecode = fs.readFileSync(
    path.join(outputDir, "FiatTokenV2_2.bytecode"),
    "utf8"
  );
  const fiatTokenAbi = JSON.parse(
    fs.readFileSync(path.join(outputDir, "FiatTokenV2_2.abi"), "utf8")
  );

  // 컨트랙트 인터페이스 생성
  const contractInterface = new ethers.Interface(fiatTokenAbi);

  // initialize 함수 파라미터 설정 (예시 - 실제 배포시 수정 필요)
  const initializeParams = {
    tokenName: "USD Coin",
    tokenSymbol: "USDC",
    tokenCurrency: "USD",
    tokenDecimals: 6,
    newMasterMinter: "0x0000000000000000000000000000000000000000",
    newPauser: "0x0000000000000000000000000000000000000000",
    newBlacklister: "0x0000000000000000000000000000000000000000",
    newOwner: "0x0000000000000000000000000000000000000000",
  };

  // initialize 함수 인코딩
  const initializeData = contractInterface.encodeFunctionData("initialize", [
    initializeParams.tokenName,
    initializeParams.tokenSymbol,
    initializeParams.tokenCurrency,
    initializeParams.tokenDecimals,
    initializeParams.newMasterMinter,
    initializeParams.newPauser,
    initializeParams.newBlacklister,
    initializeParams.newOwner,
  ]);

  // 배포 데이터 생성
  const deploymentData = {
    bytecode: fiatTokenBytecode,
    initializeData: initializeData,
    fullBytecode: fiatTokenBytecode + initializeData.slice(2), // 0x 제거
    initializeParams: initializeParams,
  };

  // 결과 저장
  fs.writeFileSync(
    path.join(outputDir, "deployment_data.json"),
    JSON.stringify(deploymentData, null, 2)
  );

  console.log("Deployment data generated successfully!");
  console.log("Full bytecode length:", deploymentData.fullBytecode.length);
  console.log("Initialize data length:", initializeData.length);
  console.log("Files saved in:", outputDir);
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
