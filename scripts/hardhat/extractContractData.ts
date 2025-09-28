import { artifacts, ethers } from "hardhat";
import fs from "fs";
import path from "path";

async function main() {
  // 컨트랙트 아티팩트 가져오기
  const FiatTokenV2_2Artifact = await artifacts.readArtifact("FiatTokenV2_2");
  const SignatureCheckerArtifact = await artifacts.readArtifact(
    "SignatureChecker"
  );

  // ABI 저장
  const abi = JSON.stringify(FiatTokenV2_2Artifact.abi, null, 2);

  // 결과 저장할 디렉토리 생성
  const outputDir = path.join(__dirname, "../../deployment_data");
  if (!fs.existsSync(outputDir)) {
    fs.mkdirSync(outputDir);
  }

  // 바이트코드와 ABI 파일로 저장
  fs.writeFileSync(
    path.join(outputDir, "FiatTokenV2_2.bytecode"),
    FiatTokenV2_2Artifact.bytecode
  );
  fs.writeFileSync(path.join(outputDir, "FiatTokenV2_2.abi"), abi);

  // SignatureChecker 라이브러리 바이트코드 저장
  fs.writeFileSync(
    path.join(outputDir, "SignatureChecker.bytecode"),
    SignatureCheckerArtifact.bytecode
  );
  fs.writeFileSync(
    path.join(outputDir, "SignatureChecker.abi"),
    JSON.stringify(SignatureCheckerArtifact.abi, null, 2)
  );

  console.log("Contract data extracted successfully!");
  console.log(
    "FiatTokenV2_2 Bytecode length:",
    FiatTokenV2_2Artifact.bytecode.length
  );
  console.log(
    "SignatureChecker Bytecode length:",
    SignatureCheckerArtifact.bytecode.length
  );
  console.log("Files saved in:", outputDir);

  // 링킹 정보 출력
  console.log("\nLibrary Linking Information:");
  console.log(
    "SignatureChecker reference in bytecode:",
    FiatTokenV2_2Artifact.bytecode.includes("__$")
  );
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
